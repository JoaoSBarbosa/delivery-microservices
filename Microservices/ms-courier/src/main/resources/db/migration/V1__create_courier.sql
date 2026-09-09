CREATE TABLE courier
(
    id                              UUID NOT NULL PRIMARY KEY,
    name                            VARCHAR(255),
    phone_number                    VARCHAR(255),
    full_filled_deliveries_quantity INTEGER,
    pending_deliveries_quantity     INTEGER,
    last_full_filled_delivery_at    TIMESTAMP WITH TIME ZONE

);