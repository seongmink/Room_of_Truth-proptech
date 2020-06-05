package com.roomoftruth.rot.dto;

import com.roomoftruth.rot.domain.Contract;
import com.roomoftruth.rot.domain.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FindAllContract {
        private String address;
        private String floor;
        private String ho;
        private String latitude;
        private String longitude;

        public FindAllContract(Contract contract){
            this.address = contract.getAddress();
            this.floor = contract.getFloor();
            this.ho = contract.getHo();
            this.latitude = contract.getLatitude();
            this.longitude = contract.getLongitude();
        }

        public FindAllContract(Status status){
            this.address = status.getAddress();
            this.floor = status.getFloor();
            this.ho = status.getHo();
            this.latitude = status.getLatitude();
            this.longitude = status.getLongitude();
        }
}
