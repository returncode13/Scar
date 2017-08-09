/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  sharath nair <sharath.nair@polarcus.com>
 * Created: Aug 9, 2017
Default insert values that need to exist before the application runs
 */

INSERT INTO obpmanager.doubttype
    (iddoubttype,name)
SELECT 1,'time'
WHERE
    NOT EXISTS(
        SELECT iddoubttype FROM obpmanager.doubttype WHERE iddoubttype = 1
);

INSERT INTO obpmanager.doubttype
    (iddoubttype,name)
SELECT 2,'traces'
WHERE
    NOT EXISTS(
        SELECT iddoubttype FROM obpmanager.doubttype WHERE iddoubttype = 2
);

INSERT INTO obpmanager.doubttype
    (iddoubttype,name)
SELECT 3,'qc'
WHERE
    NOT EXISTS(
        SELECT iddoubttype FROM obpmanager.doubttype WHERE iddoubttype = 3
);