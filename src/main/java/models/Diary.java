package models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 日記データのDTOモデル
 *
 */
@Table(name = JpaConst.TABLE_DIARY)
@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_DIARY_GET_ALL,
            query = JpaConst.Q_DIARY_GET_ALL_DEF),
    @NamedQuery(
            name = JpaConst.Q_DIARY_COUNT,
            query = JpaConst.Q_DIARY_COUNT_DEF)
//    @NamedQuery(
//            name = JpaConst.Q_EMP_COUNT_RESISTERED_BY_CODE,
//            query = JpaConst.Q_EMP_COUNT_RESISTERED_BY_CODE_DEF),
//    @NamedQuery(
//            name = JpaConst.Q_EMP_GET_BY_CODE_AND_PASS,
//            query = JpaConst.Q_EMP_GET_BY_CODE_AND_PASS_DEF)
})

@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
@Entity
public class Diary {

    /**
     * id
     */
    @Id
    @Column(name = JpaConst.DIARY_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 名前
     */
    @Column(name = JpaConst.DIARY_COL_NAME, nullable = false)
    private String name;

    /**
     * いつの日報かを示す日付
     */
    @Column(name = JpaConst.DIARY_COL_REP_DATE, nullable = false)
    private LocalDate reportDate;

    /**
     * タイトル
     */
    @Column(name = JpaConst.DIARY_COL_TITLE, nullable = false)
    private String title;

    /**
     * 名前
     */
    @Column(name = JpaConst.DIARY_COL_CONTENT, nullable = false)
    private String content;

    /**
     *登録日時
     */
    @Column(name = JpaConst.DIARY_COL_CREATED_AT, nullable = false)
    private LocalDateTime createdAt;

    /**
     * 更新日時
     */
    @Column(name = JpaConst.DIARY_COL_UPDATED_AT, nullable = false)
    private LocalDateTime updatedAt;

}