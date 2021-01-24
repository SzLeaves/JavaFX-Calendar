import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class CalendarFunction implements Initializable {
    @FXML
    private Button checkBotton;

    @FXML
    private Text timeShow;

    @FXML
    private GridPane dateShow;

    @FXML
    private TextField yearsField;

    @FXML
    private TextField monthField;

    private LocalDate localDate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // 显示日历
        localDate = LocalDate.now();
        setDateShow(localDate);
    }

    // 设置指定日期显示日历
    @FXML
    void checkDateAction(ActionEvent event) {
        int year = Integer.valueOf(yearsField.getText());
        int month = Integer.valueOf(monthField.getText());

        // 输入日期非法时的异常处理
        if (year < 1970 || month < 1 || month > 12) {
            yearsField.setText("");
            monthField.setText("");

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("");
            alert.setHeaderText("输入的日期格式非法");
            alert.showAndWait();

            throw new IllegalArgumentException();
        } else {
            LocalDate date = LocalDate.of(year, month, 1);
            setDateShow(date);
        }
    }

    // 设置日历显示格式
    public void setDateShow(LocalDate localDate) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy年 MM月");
        timeShow.setText(timeFormatter.format(localDate));

        // 获取该月份第一天为星期几
        LocalDate firstDay = localDate.withDayOfMonth(1);
        int printDateIndex = firstDay.getDayOfWeek().getValue();

        // 计算这个月的天数
        int daysOfMonth = getMonthDays(localDate.getYear(), localDate.getMonthValue());

        // 输出第一行的日期
        int days = 1;
        dateShow.getChildren().clear();
        for (int column = printDateIndex; column <= 6; column++) {
            dateShow.setStyle("-fx-font-size: 20px");
            dateShow.add(new Text(String.valueOf(days)), column, 0);
            days++;
            daysOfMonth--;
        }

        // 输出余下行的日期
        for (int row = 1; row <= 6; row++) {
            for (int column = 0; column <= 6; column++) {
                dateShow.setStyle("-fx-font-size: 20px");
                dateShow.add(new Text(String.valueOf(days)), column, row);
                days++;
                daysOfMonth--;

                // 当打印完所有日期时跳出循环
                if (0 == daysOfMonth) {
                    break;
                }
            }

            if (0 == daysOfMonth) {
                break;
            }
        }

    }

    // 计算月天数
    public int getMonthDays(int year, int month) {
        // 定义总天数
        int monthDays = 0;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                //月份总天数是31天
                monthDays = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                monthDays = 30;
                break;
            // 判断2月份是否是润年 29天，其他是平年，28天。
            case 2:
                if ((year % 400 == 0) || (year % 4 == 0 && year % 100 != 0)) {
                    // 是润年
                    monthDays = 29;
                } else {
                    // 是平年
                    monthDays = 28;
                }
                break;
        }
        // 返回总天数
        return monthDays;
    }
}
