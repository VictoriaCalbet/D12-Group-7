start transaction;

use `Acme-Battle`;

revoke all privileges on `Acme-Battle`.* from 'acme-user'@'%';
revoke all privileges on `Acme-Battle`.* from 'acme-manager'@'%';

drop user 'acme-user'@'%';
drop user 'acme-manager'@'%';

drop database `Acme-Battle`;

commit;