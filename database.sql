#a potential schema for a tools database
create table user (
    username varchar(20) primary key not null,
    password varbinary(256) not null,
    email varchar(30) not null,
    role varchar(20) not null
);


create table Customer(
	username varchar(20) primary key not null,
	phoneNumber varchar (10)
);


#encrypted passwords: bob123 jill123
insert into user (username, password, role, email) values ('bob', '0x352B6F45B4FA2E8F61D8A1AC206FB11C', 'inventoryManager', "bob@yahoo.com");
insert into user (username, password, role, email) values ('jill', '0x9BF5C296D85E84D868636B0626B69ABA', 'customer', "jill@google.com");


create table ToolMaster ( 
	toolCode varchar(20) primary key not null,
	toolType varchar(20) not null,
	brand varchar(20) not null	
);
insert into ToolMaster(toolCode, toolType, brand) values ('CHNS', 'Chainsaw', 'Stihl');
insert into ToolMaster(toolCode, toolType, brand) values ('LADW', 'Ladder', 'Werner');
insert into ToolMaster(toolCode, toolType, brand) values ('JAKD', 'Jackhammer', 'DeWalt');
insert into ToolMaster(toolCode, toolType, brand) values ('JAKR', 'Jackhammer', 'Ridgid');

create table Tool (
	serialNumber varchar(40) primary key not null,
	status int not null,
	toolCode varchar(20),
	foreign key (toolCode) references ToolMaster(toolCode)
);
#status 4 is on shelf
insert into Tool(serialNumber, status, toolCode) values ('1', 4, 'CHNS');
insert into Tool(serialNumber, status, toolCode) values ('2', 4, 'LADW');
insert into Tool(serialNumber, status, toolCode) values ('3', 4, 'JAKD');
insert into Tool(serialNumber, status, toolCode) values ('4', 4, 'JAKR');

#relationship between pricing profile and toolMaster is 1x1
create table PricingProfile (
	id int not null primary key auto_increment,
	dailyCharge decimal(10,2) not null, 
	weekdayCharge BOOL not null,
	weekendCharge BOOL not null,
	holidayCharge BOOL not null,
	toolCode varchar(20),
	foreign key (toolCode) references ToolMaster(toolCode)
);

insert into PricingProfile(dailyCharge, weekdayCharge, weekendCharge, holidayCharge, toolCode) values (1.99, true, true, false, "LADW");
insert into PricingProfile(dailyCharge, weekdayCharge, weekendCharge, holidayCharge, toolCode) values (1.49, true, false, true, "CHNS");
insert into PricingProfile(dailyCharge, weekdayCharge, weekendCharge, holidayCharge, toolCode) values (2.99, true, false, false, "JAKD");
insert into PricingProfile(dailyCharge, weekdayCharge, weekendCharge, holidayCharge, toolCode) values (2.99, true, false, false, "JAKR");

create table RentalAgreement(
	id int not null primary key auto_increment,
	startDate DATE not null,
	endDate DATE not null,
	checkoutDate DATE not null,
	discount int not null,	
	dailyCharge decimal(10,2) not null,	
	chargeDays int not null,
	preDiscountCharge decimal(10,2) not null,
	serialNumber varchar(40) not null,
	customer varchar(20) not null,
	foreign key (customer) references Customer(username),
	foreign key (serialNumber) references Tool(serialNumber)
);


	