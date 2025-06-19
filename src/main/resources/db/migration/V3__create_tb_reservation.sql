CREATE TABLE tb_reservation (

    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    table_id BIGINT NOT NULL,
    reservation_date TIMESTAMP NOT NULL,
    reservation_status VARCHAR(50) NOT NULL,

    CONSTRAINT fk_reservation_user FOREIGN KEY (user_id) REFERENCES tb_user(id),
    CONSTRAINT fk_reservation_table FOREIGN KEY (table_id) REFERENCES tb_restaurant_table(id)
);