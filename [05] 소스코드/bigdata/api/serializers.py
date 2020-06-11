from .models import Contract,Favorite,Around,User,Interest
from rest_framework import serializers
from rest_framework.serializers import ValidationError

class AroundSerializer(serializers.ModelSerializer):
    class Meta:
        model = Around
        fields=[
            "around_id",
            "address",
            "latitude",
            "longitude",
            "trans",
            "comforts",
            "education",
            "medical",
            "eatery",
            "culture"
        ]

class ContractSerializer(serializers.ModelSerializer):
    around = AroundSerializer(read_only=True)
    class Meta:
        model = Contract
        fields = [
            "contract_id",
            "around",
            "exclusive",
            "floor",
            "ho",
            "kind",
            "detail",
            "cost",
            "monthly",
            "license",
            "image",
            "contract_date",
        ]
        
class FavoriteSerializer(serializers.ModelSerializer):
    class Meta:
        model = Favorite
        fields=[
            "favorite_id",
            "score",
            "around",
            "user"
        ]
        
class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields=[
            "num",
            "nickname",
        ]

class InterestSerializer(serializers.ModelSerializer):
    class Meta:
        model = Interest
        fields=[
            "interest_id",
            "user_num",
            "birth",
            "gender",
            "first",
            "second",
            "third",
            "sd",
            "sgg",
        ]




