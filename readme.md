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