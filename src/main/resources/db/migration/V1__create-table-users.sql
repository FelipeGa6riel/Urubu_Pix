create table users(

    id BIGINT NOT NULL auto_increment,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL unique,
    cpf VARCHAR(11) NOT NULL unique,
    balance DECIMAL(10, 2),

    primary key(id)
);