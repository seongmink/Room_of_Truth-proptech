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
    num = models.BigIntegerField(primary_key=True)
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
    around_id = models.BigAutoField(primary_key=True)
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


class Favorite(models.Model):
    num = models.AutoField(primary_key=True)
    score = models.IntegerField()
    building = models.ForeignKey(Building, models.DO_NOTHING, db_column='building', related_name="fav_building")
    user = models.ForeignKey('User', models.DO_NOTHING, db_column='user', related_name="fav_user")

    class Meta:
        managed = False
        db_table = 'favorite'


class Interest(models.Model):
    interest_id = models.BigAutoField(primary_key=True)
    user_num = models.ForeignKey('User', models.DO_NOTHING, db_column='user_num')
    first = models.CharField(max_length=45, blank=True, null=True)
    second = models.CharField(max_length=45, blank=True, null=True)
    third = models.CharField(max_length=45, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'interest'


class Search(models.Model):
    search_id = models.BigAutoField(primary_key=True)
    user_num = models.BigIntegerField()
    keyword = models.CharField(max_length=100, blank=True, null=True)
    date = models.DateTimeField(blank=True, null=True)
    createdat = models.DateTimeField(db_column='createdAt', blank=True, null=True)  # Field name made lowercase.
    updatedat = models.DateTimeField(db_column='updatedAt', blank=True, null=True)  # Field name made lowercase.
    user = models.ForeignKey('User', models.DO_NOTHING, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'search'


class User(models.Model):
    num = models.BigIntegerField(primary_key=True)
    nickname = models.CharField(max_length=45, blank=True, null=True)
    auth = models.CharField(max_length=10, blank=True, null=True)
    phonenum = models.CharField(db_column='phoneNum', max_length=20, blank=True, null=True)  # Field name made lowercase.
    birth = models.IntegerField(blank=True, null=True)
    gender = models.CharField(max_length=4, blank=True, null=True)
    address = models.CharField(max_length=100, blank=True, null=True)
    picture = models.CharField(max_length=200, blank=True, null=True)
    createdat = models.DateTimeField(db_column='createdAt', blank=True, null=True)  # Field name made lowercase.
    updatedat = models.DateTimeField(db_column='updatedAt', blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'user'
