package com.isas.contactapps.db.contract;

public interface SqliteSyntax {
    // TODO: 28/10/2015 add sqlite syntax here
//    Base syntax
    String _SPC_ = " ";
    String COMMA=",";
    String CREATE = "CREATE";
    String TABLE = "TABLE";
    String IF = "IF";
    String NOT = "NOT";
    String EXISTS = "EXISTS";
    String AS = "AS";
    String TEMP = "TEMP";
    String TEMPORARY = "TEMPORARY";
    String WITHOUT = "WITHOUT";
    String ROWID = "ROWID";
    String OPENING_PARENTHESIS = "(";
    String CLOSING_PARENTHESIS = ")";
    String PRIMARY = "PRIMARY";
    String KEY = "KEY";
    String DROP= "DROP";
    String LIKE = "LIKE";
    String ALTER ="ALTER";
    String ADD = "ADD";
    String COLUMN = "COLUMN";
    String SELECT = "SELECT";
    String FROM = "FROM";
    String WHERE="WHERE";
    String EQUAL = "=";
    String AND = "AND";
    String ORDER = "ORDER";
    String DESC = "DESC";
    String DISTINCT="DISTINCT";
    String COUNT = "COUNT";
    String ASTERISK = "*";
    String NOT_EQUAL = "<>";
    String IN = "IN";
    String SEMICOLON = ";";
    String MAX= "MAX";
    String BETWEEN ="BETWEEN";
    String GREATER=">";
    String SMALLER ="<";
    String LEFT = "LEFT";
    String JOIN = "JOIN";
    String GROUP = "GROUP";
    String BY = "BY";
    String ON = "ON";

//    Data type
    String NULL = "NULL";
    String INTEGER = "INTEGER";
    String REAL = "REAL";
    String TEXT = "TEXT";
    String BLOB = "BLOB";


    String CREATE_TABLE_IF_NOT_EXISTS =CREATE+ _SPC_ +TABLE+ _SPC_ +IF+ _SPC_ +NOT+ _SPC_ +EXISTS;
    String DROP_TABLE_IF_EXISTS= DROP+ _SPC_ +TABLE+ _SPC_ +IF+ _SPC_ +EXISTS;
    String PRIMARY_KEY = PRIMARY+ _SPC_ +KEY;
    String ALTER_TABLE =ALTER+_SPC_+TABLE;
    String ADD_COLUMN = ADD+_SPC_+COLUMN;
    String ORDER_BY = ORDER+_SPC_+BY;
    String SELECT_DISTINCT = SELECT+_SPC_+DISTINCT;
    String COUNT_ALL= COUNT+OPENING_PARENTHESIS+ASTERISK+CLOSING_PARENTHESIS;
    String SELECT_ALL_FROM = SELECT+_SPC_+ASTERISK+_SPC_+FROM;
    String GREATER_EQUAL = GREATER+EQUAL;
    String SMALLER_EQUAL = SMALLER+EQUAL;
    String LEFT_JOIN = LEFT+_SPC_+JOIN;
    String GROUP_BY  = GROUP+_SPC_+BY;

}
