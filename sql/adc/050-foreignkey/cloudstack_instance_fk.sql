alter table CLOUDSTACK_INSTANCE add constraint CLOUDSTACK_INSTANCE_FK1 foreign key (INSTANCE_NO) references INSTANCE (INSTANCE_NO);
