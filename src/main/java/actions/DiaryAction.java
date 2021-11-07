package actions;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.DiaryView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import services.DiaryService;

public class DiaryAction extends ActionBase {

    private DiaryService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new DiaryService();

        //メソッドを実行
        invoke();
        service.close();
    }

    /**
     * 一覧画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void index() throws ServletException, IOException {

        //指定されたページ数の一覧画面に表示する日報データを取得
        int page = getPage();
        List<DiaryView> diaries = service.getAllPerPage(page);

        //全日報データの件数を取得
        long diariesCount = service.countAll();

        putRequestScope(AttributeConst.DIARIES, diaries); //取得した日記データ
        putRequestScope(AttributeConst.DIARY_COUNT, diariesCount); //全ての日記データの件数
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_DIARY_INDEX);
    }
    /**
     * 新規登録画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void entryNew() throws ServletException, IOException {

        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン

        //日記情報の空インスタンスに、日報の日付＝今日の日付を設定する
        DiaryView dv = new DiaryView();
        dv.setReportDate(LocalDate.now());
        putRequestScope(AttributeConst.DIARY, dv); //日付のみ設定済みの日報インスタンス

        //新規登録画面を表示
        forward(ForwardConst.FW_DIARY_NEW);

    }
    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //日報の日付が入力されていなければ、今日の日付を設定
            LocalDate day = null;
            if (getRequestParam(AttributeConst.DIARY_REP_DATE) == null
                    || getRequestParam(AttributeConst.DIARY_REP_DATE).equals("")) {
                day = LocalDate.now();
            } else {
                day = LocalDate.parse(getRequestParam(AttributeConst.DIARY_REP_DATE));
            }

            //パラメータの値をもとに日記情報のインスタンスを作成する
            DiaryView dv = new DiaryView(
                    null,
                    getRequestParam(AttributeConst.DIARY_NAME),
                    day,
                    getRequestParam(AttributeConst.DIARY_TITLE),
                    getRequestParam(AttributeConst.DIARY_CONTENT),
                    null,
                    null);

            //日報情報登録
            List<String> errors = service.create(dv);

            if (errors.size() > 0) {
                //登録中にエラーがあった場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.DIARY, dv);//入力された日記情報
                putRequestScope(AttributeConst.ERR, errors);//エラーのリスト

                //新規登録画面を再表示
                forward(ForwardConst.FW_DIARY_NEW);

            } else {
                //登録中にエラーがなかった場合

                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_DIARY, ForwardConst.CMD_INDEX);
            }
        }
    }
    /**
     * 詳細画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void show() throws ServletException, IOException {

        //idを条件に日記データを取得する
        DiaryView dv = service.findOne(toNumber(getRequestParam(AttributeConst.DIARY_ID)));

        if (dv == null) {
            //該当の日記データが存在しない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);

        } else {

            //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
            String flush = getSessionScope(AttributeConst.FLUSH);
            if (flush != null) {
                putRequestScope(AttributeConst.FLUSH, flush);
                removeSessionScope(AttributeConst.FLUSH);
            }

            // リクエストスコープにパラメータをセット
            putRequestScope(AttributeConst.DIARY, dv); //取得した日記データ
            putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン

            //詳細画面を表示
            forward(ForwardConst.FW_DIARY_SHOW);
        }
    }
    /**
     * 物理削除を行う
     * @throws ServletException
     * @throws IOException
     */
    public void destroy() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //idを条件に従業員データを論理削除する
            service.destroy(toNumber(getRequestParam(AttributeConst.DIARY_ID)));

            //セッションに削除完了のフラッシュメッセージを設定
            putSessionScope(AttributeConst.FLUSH, MessageConst.I_DELETED.getMessage());

            //一覧画面にリダイレクト
            redirect(ForwardConst.ACT_DIARY, ForwardConst.CMD_INDEX);
        }

    }

    /**
     * 編集画面を表示
     * @throws ServletException
     * @throws IOException
     */
    public void edit() throws ServletException, IOException {

//      System.out.println("edit" + toNumber(getRequestParam(AttributeConst.DIARY_ID)));

        //idを条件に日記データを取得する
        DiaryView dv = service.findOne(toNumber(getRequestParam(AttributeConst.DIARY_ID)));

        //セッションにエラーメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        List<String> errors = getSessionScope(AttributeConst.ERR);
        if (errors != null) {
            putRequestScope(AttributeConst.ERR, errors);
            removeSessionScope(AttributeConst.ERR);
        }
        System.out.println(dv.getName());
        // リクエストスコープにパラメータをセット
        putRequestScope(AttributeConst.DIARY, dv); //取得した日記データ
        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン

        //          //idを条件に従業員データを論理削除する
        //          service.destroy(toNumber(getRequestParam(AttributeConst.DIARY_ID)));
        //
        //          //セッションに削除完了のフラッシュメッセージを設定
        //          putSessionScope(AttributeConst.FLUSH, MessageConst.I_DELETED.getMessage());

        forward(ForwardConst.FW_DIARY_EDIT);

    }
    /**
     * 更新を行う
     * @throws ServletException
     * @throws IOException
     */
    public void update() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {
            //idを条件に日記データを取得する
            DiaryView dv = service.findOne(toNumber(getRequestParam(AttributeConst.DIARY_ID)));

            //日報の日付が入力されていなければ、今日の日付を設定
            LocalDate day = null;
            if (getRequestParam(AttributeConst.DIARY_REP_DATE) == null
                    || getRequestParam(AttributeConst.DIARY_REP_DATE).equals("")) {
                day = LocalDate.now();
            } else {
                day = LocalDate.parse(getRequestParam(AttributeConst.DIARY_REP_DATE));
            }

            //パラメータの値をもとに日記インスタンスを更新する
            dv.setName(getRequestParam(AttributeConst.DIARY_NAME));
            dv.setReportDate(day);
            dv.setTitle(getRequestParam(AttributeConst.DIARY_TITLE));
            dv.setContent(getRequestParam(AttributeConst.DIARY_CONTENT));

            //日報情報更新
            List<String> errors = service.update(dv);

            if (errors.size() > 0) {
                //登録中にエラーがあった場合

//              putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
//              putSessionScope(AttributeConst.DIARY, dv);//入力された日記情報
                putSessionScope(AttributeConst.ERR, errors);//エラーのリスト

                //編集画面を再表示
//              forward(ForwardConst.FW_DIARY_EDIT);
                response.sendRedirect(context + "/?action=Diary&command=edit&id=" + dv.getId());


            } else {
                //登録中にエラーがなかった場合

                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_DIARY, ForwardConst.CMD_INDEX);
            }
        }
    }

}