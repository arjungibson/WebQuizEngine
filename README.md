# Web Quiz Engine
Spring RestController to access and create quizzes.

## Description
The goal of the Quiz Engine was to create a multi-user web service that can create, read, answer questions, and delete users, and used MVC as the design framework.

The questions have the following input:

```json
{
  "title": "Coffee drinks",
  "text": "Select only coffee drinks.",
  "options": ["Americano","Tea","Cappuccino","Sprite"],
  "answer": [0,2]
}
```

And when retrieved, they have the following output:

```json
{
  "id": 1,
  "title": "Coffee drinks",
  "text": "Select only coffee drinks.",
  "options": ["Americano","Tea","Cappuccino","Sprite"]
}
```

Users are registed without auth using this format:

```json
{
  "email": "test@gmail.com",
  "password": "secret"
}
```

Basic auth was created using Spring Security.  The passwords are encrypted and stored in the database using BCrypt.
Deletion is permission based and only the author of a particular quiz can delete it.


## Authors and acknowledgment

This project was designed by Hyperskill.

## License

MIT License  See LICENSE.
