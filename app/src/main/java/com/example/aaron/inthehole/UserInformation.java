package com.example.aaron.inthehole;

    public class UserInformation {

        private String name;
        private String age;
        private String gender;
    private String handicap;

        public UserInformation(){ // Details For View Information
            //each of the values are then set and get for further use

        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }
    public String getHandicap() {
        return handicap;
    }

    public void setHandicap(String handicap) {
        this.handicap = handicap;
    }
    }

