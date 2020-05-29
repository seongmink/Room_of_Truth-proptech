# This is an auto-generated Django model module.
# You'll have to do the following manually to clean this up:
#   * Rearrange models' order
#   * Make sure each model has one field with primary_key=True
#   * Make sure each ForeignKey and OneToOneField has `on_delete` set to the desired behavior
#   * Remove `managed = False` lines if you wish to allow Django to create, modify, and delete the table
# Feel free to rename the models, but don't rename db_table values or field names.
from django.db import models

class Address(models.Model):
    num = models.AutoField(primary_key=True)
    roadaddress = models.CharField(db_column='roadAddress', unique=True, max_length=100, blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'address'


class Agent(models.Model):
    num = models.OneToOneField('User', models.DO_NOTHING, db_column='num', primary_key=True)
    name = models.CharField(max_length=45, blank=True, null=True)
    representative = models.CharField(max_length=45, blank=True, null=True)
    license = models.CharField(max_length=45, blank=True, null=True)
    address = models.CharField(max_length=100, blank=True, null=True)
    picture = models.CharField(max_length=200, blank=True, null=True)
    phonenum = models.CharField(db_column='phoneNum', max_length=20, blank=True, null=True)  # Field name made lowercase.
    count = models.IntegerField(blank=True, null=True)
    point = models.IntegerField(blank=True, null=True)
    delflag = models.IntegerField(blank=True, null=True)
    createdat = models.DateTimeField(db_column='createdAt', blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'agent'


class Around(models.Model):
    num = models.AutoField(primary_key=True)
    address = models.CharField(unique=True, max_length=100, blank=True, null=True)
    trans = models.IntegerField(blank=True, null=True)
    comforts = models.IntegerField(blank=True, null=True)
    education = models.IntegerField(blank=True, null=True)
    medical = models.IntegerField(blank=True, null=True)
    eatery = models.IntegerField(blank=True, null=True)
    culture = models.IntegerField(blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'around'


class Building(models.Model):
    num = models.AutoField(primary_key=True)
    address = models.CharField(max_length=100, blank=True, null=True)
    sd = models.CharField(max_length=20, blank=True, null=True)
    sgg = models.CharField(max_length=20, blank=True, null=True)
    emd = models.CharField(max_length=20, blank=True, null=True)
    latitude = models.CharField(max_length=45, blank=True, null=True)
    longitude = models.CharField(max_length=45, blank=True, null=True)
    exclusive = models.CharField(max_length=10, blank=True, null=True)
    floor = models.CharField(max_length=10, blank=True, null=True)
    ho = models.CharField(max_length=10, blank=True, null=True)
    kind = models.CharField(max_length=10, blank=True, null=True)
    detail = models.CharField(max_length=10, blank=True, null=True)
    cost = models.CharField(max_length=10, blank=True, null=True)
    monthly = models.CharField(max_length=10, blank=True, null=True)
    license = models.CharField(max_length=45, blank=True, null=True)
    image = models.CharField(max_length=200, blank=True, null=True)
    contractedat = models.DateField(db_column='contractedAt', blank=True, null=True)  # Field name made lowercase.
    createdat = models.DateTimeField(db_column='createdAt', blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'building'


class Search(models.Model):
    num = models.OneToOneField('User', models.DO_NOTHING, db_column='num', primary_key=True)
    keyword = models.CharField(max_length=100)
    date = models.DateTimeField(blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'search'
        unique_together = (('num', 'keyword'),)


class User(models.Model):
    num = models.BigIntegerField(primary_key=True)
    nickname = models.CharField(max_length=45, blank=True, null=True)
    auth = models.CharField(max_length=10, blank=True, null=True)
    phonenum = models.CharField(db_column='phoneNum', max_length=20, blank=True, null=True)  # Field name made lowercase.
    age = models.IntegerField(blank=True, null=True)
    gender = models.CharField(max_length=4, blank=True, null=True)
    address = models.CharField(max_length=100, blank=True, null=True)
    picture = models.CharField(max_length=200, blank=True, null=True)
    createdat = models.DateTimeField(db_column='createdAt', blank=True, null=True)  # Field name made lowercase.
    enteredat = models.DateTimeField(db_column='enteredAt', blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'user'
