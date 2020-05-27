from .models import Building
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
            "detail",
            "cost",
            "monthly",
            "license",
            "image",
            "contractedat",
            "createdat",
        ]
