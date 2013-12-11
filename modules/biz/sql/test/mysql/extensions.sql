-- extend a class through 'soft-coded' properties
-- this is a temporary implementation that uses hibernate's dynamic-component feature

ALTER TABLE people_organization 
	 ADD COLUMN numEmployees integer unsigned
	,ADD COLUMN isForProfit bit
	,ADD COLUMN descr text
	,ADD COLUMN memo1 text
	,ADD COLUMN memo2 text
;
