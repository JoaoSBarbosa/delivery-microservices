CREATE TABLE item
(
    id          UUID    NOT NULL PRIMARY KEY,
    name        VARCHAR(255),
    description VARCHAR(255),
    quantity    INTEGER NOT NULL,
    delivery_id UUID    NOT NULL,
    CONSTRAINT fk_item_delivery FOREIGN KEY (delivery_id) REFERENCES delivery (id)
)