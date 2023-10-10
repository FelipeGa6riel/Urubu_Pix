create table transactions(

      id bigint not null auto_increment,
      sender_id bigint not null,
      amount DECIMAL(10,2),
      receiver_id bigint not null,
      data datetime not null,

      primary key(id),
      constraint fk_transactions_sender_id foreign key(sender_id) references users(id),
      constraint fk_transactions_receiver_id foreign key(receiver_id) references users(id)
);