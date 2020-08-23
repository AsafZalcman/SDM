package enums;

public enum PurchaseForm {
    QUANTITY("Quantity"){
        @Override
        public String toString() {
            return getValue();
        }
    }, WEIGHT("Weight"){
        @Override
        public String toString() {
            return getValue();
        }
    };
    private final String m_Value;
    PurchaseForm(String i_Value)
    {
        m_Value=i_Value;
    }

    public String getValue()
    {
        return m_Value;
    }

}
