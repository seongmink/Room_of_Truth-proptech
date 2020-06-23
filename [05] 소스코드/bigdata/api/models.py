# This is an auto-generated Django model module.
# You'll have to do the following manually to clean this up:
#   * Rearrange models' order
#   * Make sure each model has one field with primary_key=True
#   * Make sure each ForeignKey and OneToOneField has `on_delete` set to the desired behavior
#   * Remove `managed = False` lines if you wish to allow Django to create, modify, and delete the table
# Feel free to rename the models, but don't rename db_table values or field names.
from django.db import models


class Agent(models.Model):
    attention_id = models.BigAutoField(primary_key=True)
    created_at = models.DateTimeField(blank=True, null=True)
    updated_at = models.DateTimeField(blank=True, null=True)
    address = models.CharField(max_length=255, blank=True, null=True)
    count = models.IntegerField()
    license = models.CharField(max_length=255, blank=True, null=True)
    name = models.CharField(max_length=255, blank=True, null=True)
    phone_num = models.CharField(max_length=255, blank=True, null=True)
    picture = models.CharField(max_length=255, blank=True, null=True)
    point = models.IntegerField()
    representative = models.CharField(max_length=255, blank=True, null=True)
    user_num = models.ForeignKey('User', models.DO_NOTHING, db_column='user_num', blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'agent'


class Around(models.Model):
    around_id = models.BigAutoField(primary_key=True)
    address = models.CharField(unique=True, max_length=100, blank=True, null=True)
    longitude = models.CharField(max_length=45, blank=True, null=True)
    latitude = models.CharField(max_length=45, blank=True, null=True)
    trans = models.IntegerField(blank=True, null=True)
    comforts = models.IntegerField(blank=True, null=True)
    education = models.IntegerField(blank=True, null=True)
    medical = models.IntegerField(blank=True, null=True)
    eatery = models.IntegerField(blank=True, null=True)
    culture = models.IntegerField(blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'around'


class Contract(models.Model):
    contract_id = models.BigAutoField(primary_key=True)
    around = models.ForeignKey('Around', on_delete=models.CASCADE, db_column='around_around_id', blank=True, null=True, related_name="contract_around")
    exclusive = models.CharField(max_length=10, blank=True, null=True)
    floor = models.CharField(max_length=10, blank=True, null=True)
    ho = models.CharField(max_length=10, blank=True, null=True)
    kind = models.CharField(max_length=10, blank=True, null=True)
    detail = models.CharField(max_length=10, blank=True, null=True)
    cost = models.BigIntegerField(blank=True, null=True)
    monthly = models.CharField(max_length=10, blank=True, null=True)
    license = models.CharField(max_length=45, blank=True, null=True)
    image = models.CharField(max_length=200, blank=True, null=True)
    contract_date = models.DateField(blank=True, null=True)
    created_at = models.CharField(max_length=45, blank=True, null=True)
    is_expired = models.CharField(max_length=10, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'contract'


class Favorite(models.Model):
    favorite_id = models.AutoField(primary_key=True)
    score = models.IntegerField()
    around = models.ForeignKey('Around', on_delete=models.CASCADE, db_column='around_around_id', blank=True, null=True, related_name="fav_around")
    user = models.ForeignKey('User', on_delete=models.CASCADE, db_column='user_num', blank=True, null=True, related_name="fav_user")

    class Meta:
        managed = False
        db_table = 'favorite'


class Interest(models.Model):
    interest_id = models.BigAutoField(primary_key=True)
    birth = models.IntegerField(blank=True, null=True)
    first = models.CharField(max_length=255, blank=True, null=True)
    gender = models.CharField(max_length=255, blank=True, null=True)
    sd = models.CharField(max_length=255, blank=True, null=True)
    second = models.CharField(max_length=255, blank=True, null=True)
    sgg = models.CharField(max_length=255, blank=True, null=True)
    third = models.CharField(max_length=255, blank=True, null=True)
    user_num = models.ForeignKey('User', models.DO_NOTHING, db_column='user_num', blank=True, null=True, related_name="interest_user")

    class Meta:
        managed = False
        db_table = 'interest'


class Search(models.Model):
    search_id = models.BigAutoField(primary_key=True)
    created_at = models.DateTimeField(blank=True, null=True)
    updated_at = models.DateTimeField(blank=True, null=True)
    keyword = models.CharField(max_length=255, blank=True, null=True)
    user_num = models.ForeignKey('User', models.DO_NOTHING, db_column='user_num', blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'search'


class User(models.Model):
    num = models.BigIntegerField(primary_key=True)
    created_at = models.DateTimeField(blank=True, null=True)
    updated_at = models.DateTimeField(blank=True, null=True)
    auth = models.CharField(max_length=255, blank=True, null=True)
    nickname = models.CharField(max_length=255, blank=True, null=True)
    picture = models.CharField(max_length=255, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'user'
