INSERT INTO sellers (id, seller_name)
VALUES
    (1, 'Thomaz');

INSERT INTO payments (payment_id, balance, seller_id, payment_status)
VALUES
    (1, -10, 1, 'PENDENTE'),
    (2, -20, 1, 'PENDENTE'),
    (3, -30, 1, 'PENDENTE');