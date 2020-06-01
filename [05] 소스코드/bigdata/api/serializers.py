from .models import Building,Favorite,Around,User
from rest_framework import serializers
from rest_framework.serializers import ValidationError

class BuildingSerializer(serializers.ModelSerializer):
    class Meta:
        model = Building
        fields = [
            "num",
            "address",
            "sd",
            "sgg",
            "emd",
            "latitude",
            "longitude",
            "exclusive",
            "floor",
            "ho",
            "detail",
            "cost",
            "monthly",
            "license",
            "image",
            "contractedat",
            "createdat",
        ]
class AroundSerializer(serializers.ModelSerializer):
    class Meta:
        model = Around
        fields=[
            "around_id",
            "address",
            "trans",
            "comforts",
            "education",
            "medical",
            "eatery",
            "culture"
        ]
class FavoriteSerializer(serializers.ModelSerializer):
    class Meta:
        model = Favorite
        fields=[
            "num",
            "score",
            "building",
            "user"
        ]

        
class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields=[
            "num",
            "nickname",
            "gender",
            "address"
        ]


