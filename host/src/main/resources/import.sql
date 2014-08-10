-- MySQL
insert into SystemUser select null, 'admin', 'admin'
insert into GameVariant select null, 'VK'
insert into InGameuser select null, 'vasyapupkin', 1, 1
insert into Ticket select null, 'dummy action1', 1, null, 1
insert into Ticket select null, 'dummy action2', 2, null, 1
insert into Ticket select null, 'dummy action3', 3, null, 1
insert into Ticket select null, 'dummy action', 1, current_timestamp, 1