package constants;

/**
 * DB関連の項目値を定義するインターフェース
 * ※インターフェイスに定義した変数は public static final 修飾子がついているとみなされる
 */
public interface JpaConst {

    //persistence-unit名
    String PERSISTENCE_UNIT_NAME = "diary_system";

    //データ取得件数の最大値
    int ROW_PER_PAGE = 5; //1ページに表示するレコードの数

    //日記テーブル
    String TABLE_DIARY = "diaries"; //テーブル名
    //日記テーブルカラム
    String DIARY_COL_ID = "id"; //id
    String DIARY_COL_NAME = "name"; //氏名
    String DIARY_COL_REP_DATE = "reportDate";
    String DIARY_COL_TITLE = "title"; //タイトル
    String DIARY_COL_CONTENT = "content"; //内容
    String DIARY_COL_CREATED_AT = "created_at"; //登録日時
    String DIARY_COL_UPDATED_AT = "updated_at"; //更新日時

    //JPQL内パラメータ（Diaryモデルのフィールド値）
    String JPQL_PARM_ID = "id"; //日記番号
    String JPQL_PARM_NAME = "name"; //名前
    String JPQL_PARM_REPORT_DATE = "reportDate"; //日付
    String JPQL_PARM_TITLE = "title"; //タイトル
    String JPQL_PARM_CONTENT = "content"; //内容
    String JPQL_PARM_CREATED_AT = "createdAt"; //投稿日時
    String JPQL_PARM_UPDATED_AT = "updatedAt"; //更新日時

    String ENTITY_DIARY = "diary"; //日記


    //全ての従業員をidの降順に取得する
    //    String Q_EMP_GET_ALL = ENTITY_EMP + ".getAll"; //name
    //    String Q_EMP_GET_ALL_DEF = "SELECT e FROM Employee AS e ORDER BY e.id DESC"; //query
    //    //全ての従業員の件数を取得する
    //    String Q_EMP_COUNT = ENTITY_EMP + ".count";
    //    String Q_EMP_COUNT_DEF = "SELECT COUNT(e) FROM Employee AS e";

    //    //指定した社員番号を保持する従業員の件数を取得する
    //    String Q_EMP_COUNT_RESISTERED_BY_CODE = ENTITY_EMP + ".countRegisteredByCode";
    //    String Q_EMP_COUNT_RESISTERED_BY_CODE_DEF = "SELECT COUNT(e) FROM Employee AS e WHERE e.code = :" + JPQL_PARM_CODE;

    //NamedQueryの nameとquery
    //全ての日記をidの降順に取得する
    String Q_DIARY_GET_ALL = ENTITY_DIARY + ".getAll";
    String Q_DIARY_GET_ALL_DEF = "SELECT d FROM Diary AS d ORDER BY d.id DESC";

    //全ての日記の件数を取得する
    String Q_DIARY_COUNT = ENTITY_DIARY + ".count";
    String Q_DIARY_COUNT_DEF = "SELECT COUNT(d) FROM Diary AS d";

    //    //指定した従業員が作成した日報を全件idの降順で取得する
    //    String Q_REP_GET_ALL_MINE = ENTITY_REP + ".getAllMine";
    //    String Q_REP_GET_ALL_MINE_DEF = "SELECT r FROM Report AS r WHERE r.employee = :" + JPQL_PARM_EMPLOYEE + " ORDER BY r.id DESC";
    //    //指定した従業員が作成した日報の件数を取得する
    //    String Q_REP_COUNT_ALL_MINE = ENTITY_REP + ".countAllMine";
    //    String Q_REP_COUNT_ALL_MINE_DEF = "SELECT COUNT(r) FROM Report AS r WHERE r.employee = :" + JPQL_PARM_EMPLOYEE;

}