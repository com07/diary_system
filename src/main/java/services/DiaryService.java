package services;

import java.time.LocalDateTime;
import java.util.List;

import actions.views.DiaryConverter;
import actions.views.DiaryView;
import constants.JpaConst;
import models.Diary;
import models.validators.DiaryValidator;

public class DiaryService extends ServiceBase {

    /**
     * 指定されたページ数の一覧画面に表示する日記データを取得し、DiaryViewのリストで返却する
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<DiaryView> getAllPerPage(int page) {

        List<Diary> diaries = em.createNamedQuery(JpaConst.Q_DIARY_GET_ALL, Diary.class)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return DiaryConverter.toViewList(diaries);
    }

    /**
     * 日記テーブルのデータの件数を取得し、返却する
     * @return データの件数
     */
    public long countAll() {
        long diaries_count = (long) em.createNamedQuery(JpaConst.Q_DIARY_COUNT, Long.class)
                .getSingleResult();
        return diaries_count;
    }

    /**
     * idを条件に取得したデータをDiaryViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public DiaryView findOne(int id) {
        return DiaryConverter.toView(findOneInternal(id));
    }

    /**
     * 画面から入力された日記の登録内容を元にデータを1件作成し、日記テーブルに登録する
     * @param dv 日記の登録内容
     * @return バリデーションで発生したエラーのリスト
     */
    public List<String> create(DiaryView dv) {
        List<String> errors = DiaryValidator.validate(dv);
        if (errors.size() == 0) {
            LocalDateTime ldt = LocalDateTime.now();
            dv.setCreatedAt(ldt);
            dv.setUpdatedAt(ldt);
            createInternal(dv);
        }

        //バリデーションで発生したエラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * 画面から入力された日報の登録内容を元に、日報データを更新する
     * @param dv 日報の更新内容
     * @return バリデーションで発生したエラーのリスト
     */
    public List<String> update(DiaryView dv) {

        //バリデーションを行う
        List<String> errors = DiaryValidator.validate(dv);

        if (errors.size() == 0) {

            //更新日時を現在時刻に設定
            LocalDateTime ldt = LocalDateTime.now();
            dv.setUpdatedAt(ldt);

            updateInternal(dv);
        }

        //バリデーションで発生したエラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * idを条件にデータを1件取得する
     * @param id
     * @return 取得データのインスタンス
     */
    private Diary findOneInternal(int id) {
        return em.find(Diary.class, id);
    }

    /**
     * 日報データを1件登録する
     * @param dv 日記データ
     */
    private void createInternal(DiaryView dv) {

        em.getTransaction().begin();
        em.persist(DiaryConverter.toModel(dv));
        em.getTransaction().commit();

    }

    /**
     * 日記データを更新する
     * @param dv 日記データ
     */
    private void updateInternal(DiaryView dv) {

        em.getTransaction().begin();
        Diary d = findOneInternal(dv.getId());
        DiaryConverter.copyViewToModel(d, dv);
        em.getTransaction().commit();

    }

     /**
     * idを条件に日記データを物理削除する
     * @param id
     */
    public void destroy(Integer id) {

        //idを条件に登録済みの従業員情報を取得する
        Diary d = findOneInternal(id);

        //物理削除処理を行う
        em.getTransaction().begin();
        em.remove(d);
        em.getTransaction().commit();

    }


}