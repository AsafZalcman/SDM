package utils;

public enum PurchaseForm {
    QUANTITY{
        @Override
        public String toString() {
            return "$classname{}";
        }
    }, WEIGHT{
        @Override
        public String toString() {
            return "$classname{}";
        }
    };

}
