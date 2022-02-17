INSERT INTO TB_BASEUSER(name, email,phone_number,address) VALUES('Erick', 'erick@gmail.com', '(15)99818-1242', 'rua do erick, 123');
INSERT INTO TB_BASEUSER(name, email,phone_number,address) VALUES('Ana', 'ana@gmail.com', '(95)12475-1242', 'rua da ana, 22');
INSERT INTO TB_BASEUSER(name, email,phone_number,address) VALUES('Laura', 'laura@outlook.com', '(11)99555-6652', 'rua da lau, 14523');
INSERT INTO TB_BASEUSER(name, email,phone_number,address) VALUES('Pedro', 'pe@outlook.com', '(11)92626-6132', 'rua pedrao, 95425');
INSERT INTO TB_BASEUSER(name, email,phone_number,address) VALUES('Paulo', 'pa@outlook.com', '(11)95659-1123', 'paulo ruA, 3');

INSERT INTO TB_PASSENGER(passenger_id, emergency_phone_number) VALUES (4, '(15)99716-3158');
INSERT INTO TB_PASSENGER(passenger_id, emergency_phone_number) VALUES (5, '(15)70700-7070');

INSERT INTO TB_CREW(level,office,salary,crew_id) VALUES ('INTERN','PILOT', 3500.50, 1);
INSERT INTO TB_CREW(level,office,salary,crew_id) VALUES ('PLENO','FLIGHTATTENDANT', 7000.99, 2);
INSERT INTO TB_CREW(level,office,salary,crew_id) VALUES ('SENIOR','PILOT', 22590.90, 3);

INSERT INTO TB_FLIGTH(arrival_place, available_seats, category, crew_climbed, crew_needed, occupied_seats, starting_place) VALUES ('chegada', 50, 'PARTICULAR', 1, 5, 13, 'partida');

INSERT INTO TB_FLIGTH(arrival_place, available_seats, category, crew_climbed, crew_needed, occupied_seats, starting_place) VALUES ('natal', 100, 'COMMERCIAL', 1, 10, 1, 'sp');
