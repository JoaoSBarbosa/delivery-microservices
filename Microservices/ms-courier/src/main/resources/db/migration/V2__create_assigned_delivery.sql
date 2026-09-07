CREATE TABLE assigned_delivery
(
    id          UUID NOT NULL PRIMARY KEY,
    courier_id  UUID NOT NULL,
    assigned_at TIMESTAMP WITH TIME ZONE,
    CONSTRAINT fk_assigned_delivery_courier FOREIGN KEY (courier_id) REFERENCES courier (id)
);