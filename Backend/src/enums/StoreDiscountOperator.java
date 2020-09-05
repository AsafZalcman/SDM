package enums;

public enum StoreDiscountOperator {
    IRRELEVANT("IRRELEVANT") {
        @Override
        public String toString() {
            return getValue();
        }
    }, ONE_OF("ONE-OF") {
        @Override
        public String toString() {
            return getValue();
        }
    },
    ALL_OR_NOTHING("ALL-OR-NOTHING") {
        @Override
        public String toString() {
            return getValue();
        }
    };
    private final String m_Value;

    StoreDiscountOperator(String i_Value) {
        m_Value = i_Value;
    }
    public String getValue() {
        return m_Value;
    }
}
