package enums;

public enum UserType {
    CUSTOMER("Customer"){
        @Override
        public String toString() {
            return getValue();
        }
    }, MANAGER("Manager"){
        @Override
        public String toString() {
            return getValue();
        }
    };
    private final String m_Value;
    UserType(String i_Value)
    {
        m_Value=i_Value;
    }

    public String getValue()
    {
        return m_Value;
    }
}
