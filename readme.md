# Curso Spring boot Data JPA, profesor: AmigosCode

https://www.youtube.com/watch?v=8SGI_XS5OPw&list=WL&index=127&t=75s&ab_channel=Amigoscode

## overview

Spring Data JPA es una abstraccion, y hiberante es una implementaciones de esta abstraccion.

nosotros en nuestro codigo usamos JPA para ser independientes de la implementacion de hibernate y poder cambiarlo facilmente.

Hibernate es un ORM (object relational mapping) que nos permite mapear objetos de nuestra aplicacion a una base de datos.

![overview](/docs/overview.png "overview")

## Corriendo la aplicacion y conectarse a db postgres docker 9:07

hay que editar el archivo resources/application.properties para que coincida con los datos de tu DB postgres.

En mi caso utilizare un DB postgres con docker.

crearemos un docker-compose.yml con la configuracion para levantar facilmente en contenedor postgres configurado.
tambien creamos un .env de las propiedades que tendra el postgres. y seteamos usuario y password en el application.properties.

Creamos construimos el contenedor con:

```bash
docker-compose -f docker-compose.yml up --build
```

para ejecutarlo:

```bash
docker-compose -f docker-compose.yml up
```

pararlo:

```bash
docker-compose -f docker-compose.yml down
```

## Creando tabla estudiande con JPA

![estudiante](/docs/estudiante.png "estudiante")

Crearemos una clase Java que tendra dos anotaciones @Entity y @Table. Que le diran a JPA y la implementacion que usemos que esta clase es una entidad y que se mapeara a una tabla.

para definir las otras cosas como que el email debe ser unico, los datos tienen que ser no nulos, y que id tiene que ser un valor serial autoincremental, tambien lo diremos a traves de anotaciones.

### Importante

nuestra clase con sus atributos.
tendra que tener un constructor vacio.
y ademas todos los getters y setters de las propiedades, ya que esto es usado por Hibernate. (crea el objeto vacio y con setters lo rellena.)

pero como llenar todo de getters y setters deja el codigo sucio utilizaremos una libreria que lo hara por nostros con una simple anotacion.

## agregar Lombok

https://projectlombok.org/setup/maven

copiar todo lo de <dependecy> y agregarlo a .pom, luego re cargar el proyecto. para añadirlo.

luego agregar arriba de la clase Estudiante las anotaciones @Getter, @Setter

### Creando autoincrementable id

Dado nuestros requisitos de no requerir un registro secuencial estricto, y que usamos una DB no MYsql la mejor opcion en rendimiento para hibernate es usar una secuaencia. Para eso debemos declarar un secuenciador y decirle al GenerationType que usaremos secuencias.

https://www.adictosaltrabajo.com/2019/12/26/hibernate-uso-de-generationtype-y-otras-anotaciones/

### personalizando las columnas

es buena practica definir el nombre de cada columna respetando la nomenclatura de SQL snake case.

Con respecto al correo, si estuvieramos en un proyecto de spring completo podriamos usar javax.validation y utilizar el anotador @Size, que tiene mas potencia que el @Column(length=320). Ya que @Size servira para evitar instancar objetos que superen el valor creado, evitando enviarle valores no aceptables a la DB, y ademas la DB, deberia crear una columna de 320 caracteres.

despues de crear todo podemos ver en los logs de hibernate que esta todo lo que mostramos en la imagen que necesitabamos:

```bash
Hibernate: create sequence estudiante_seq start 1 increment 1
Hibernate:

    create table estudiante (
       id int8 not null,
        apellido varchar(255) not null,
        correo varchar(320) not null,
        edad int4 not null,
        nombre varchar(255) not null,
        primary key (id)
    )
Hibernate:

    alter table estudiante
       add constraint UK_f6a7ekbom6tl1l43x4ko45x0o unique (correo)
```


## mejorando el nombre del constraint unique correo

si entramos al contenedor de la db

```bash
docker exec -it amigoscode-spring-data-jpa-postgres-db psql -U braian amigoscode
```

y mostramos la informacion de la tabla estudiante.
```bash
amigoscode=# \d+ estudiante
                                                Table "public.estudiante"
  Column  |          Type          | Collation | Nullable | Default | Storage  | Compression | Stats target | Description
----------+------------------------+-----------+----------+---------+----------+-------------+--------------+-------------
 id       | bigint                 |           | not null |         | plain    |             |              |
 apellido | character varying(255) |           | not null |         | extended |             |              |
 correo   | character varying(320) |           | not null |         | extended |             |              |
 edad     | integer                |           | not null |         | plain    |             |              |
 nombre   | character varying(255) |           | not null |         | extended |             |              |
Indexes:
    "estudiante_pkey" PRIMARY KEY, btree (id)
    "uk_f6a7ekbom6tl1l43x4ko45x0o" UNIQUE CONSTRAINT, btree (correo)
```
podemos ver la restriccion es bastante larga y poco descriptiva.

cambiemos esto, para eso tenemos que ir a nuestra clase Estudiante y agregar a la anotacion @Table 

```java
@Table(name = "estudiante", uniqueConstraints = {
        @UniqueConstraint(name = "estudiante_correo_unique", columnNames = "correo")
})
```
y debemos quitar de la columna correo, la constraint unique, para que tome la de @Table.

Con esto ya si volvemos a re iniciar el servidor, podremos ver en los logs de Hibernate el cambio y ademas re ejecutando el comando que usamos para el contenedor postgres.

## Repositorio 48:49

Los repositorios nos ayudan a manipular las entidades en al DB.

crear, actualizar, eliminar, leer, y realizar consultas.

Los repositorios sirven como DAO en las POJO de Java.
(Data Access Object) (Plain Old Java Object)
estos nos ayudan reduciendo la cantidad de codigo boilerplate que hay que construir para la capa de acceso a datos.

![repositorio](/docs/repositorio.png "repositorio")

con Spring boot no es necesario crear un clase DAO, directamente lo que hacemos es crear una interfaz que hereda de JpaRepository<type,id>. Luego spring data, creara una clase que implemente esa interfaz, y generara todo el codigo. Tambien podemos definir metodos con cierta estructura y generara el codigo para ellos ej: findNombreAtributo. y tambien podemos crear metodos con nombres sin formato y con la anotacion @Query decirle cual sera la operacion.

### probemoslo

En la clase Application.java, si creamos un metodo que retorne un CommandLineRunner y tenga la anotacion @Bean , Spring boot lo ejecutara al iniciar el programa.

luego podremos ver al iniciar el programa que el log de hibernate inserta una fila.
tambien si entramos al docker y ejecutamos el comando:

```bash
table estudiante;
```	
veremos que esta la tabla con el estudiante agregado.