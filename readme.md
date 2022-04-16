# Curso Spring boot Data JPA, profesor: AmigosCode

https://www.youtube.com/watch?v=8SGI_XS5OPw&list=WL&index=127&t=75s&ab_channel=Amigoscode 
## overview

Spring Data JPA es una abstraccion, y hiberante es una implementaciones de esta abstraccion.

nosotros en nuestro codigo usamos JPA para ser independientes de la implementacion de hibernate y poder cambiarlo facilmente.

Hibernate es un ORM (object relational mapping) que nos permite mapear objetos de nuestra aplicacion a una base de datos.
 
![overview](/docs/overview.png "overview").

# Corriendo la aplicacion y conectarse a db postgres docker 9:07

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