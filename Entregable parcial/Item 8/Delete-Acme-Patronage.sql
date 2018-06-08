start transaction;
use `Acme-Patronage`;

revoke all privileges on `Acme-Patronage`.* from 'acme-user'@'%';
revoke all privileges on `Acme-Patronage`.* from 'acme-manager'@'%';

drop user 'acme-user'@'%';
drop user 'acme-manager'@'%';
drop database `Acme-Patronage`;

commit;