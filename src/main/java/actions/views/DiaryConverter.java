
package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Diary;

/**
 * 日記データのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 */
public class DiaryConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param ev EmployeeViewのインスタンス
     * @return Employeeのインスタンス
     */
    public static Diary toModel(DiaryView dv) {

        return new Diary(
                dv.getId(),
                dv.getName(),
                dv.getReportDate(),
                dv.getTitle(),
                dv.getContent(),
                dv.getCreatedAt(),
                dv.getUpdatedAt());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param e Employeeのインスタンス
     * @return EmployeeViewのインスタンス
     */
    public static DiaryView toView(Diary d) {

        if (d == null) {
            return null;
        }

        return new DiaryView(
                d.getId(),
                d.getName(),
                d.getReportDate(),
                d.getTitle(),
                d.getContent(),
                d.getCreatedAt(),
                d.getUpdatedAt());
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<DiaryView> toViewList(List<Diary> list) {
        List<DiaryView> dvs = new ArrayList<>();

        for (Diary d : list) {
            dvs.add(toView(d));
        }

        return dvs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param e DTOモデル(コピー先)
     * @param ev Viewモデル(コピー元)
     */
    public static void copyViewToModel(Diary d, DiaryView dv) {
        d.setId(dv.getId());
        d.setName(dv.getName());
        d.setReportDate(dv.getReportDate());
        d.setTitle(dv.getTitle());
        d.setContent(dv.getContent());
        d.setCreatedAt(dv.getCreatedAt());
        d.setUpdatedAt(dv.getUpdatedAt());

    }

}