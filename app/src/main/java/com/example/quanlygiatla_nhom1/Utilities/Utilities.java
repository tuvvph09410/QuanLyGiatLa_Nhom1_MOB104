package com.example.quanlygiatla_nhom1.Utilities;

import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class Utilities {
    public String NotSpecialCharacter = "Vui lòng không nhập kí tự đặc biệt";
    public String PhoneInvalid = "Số điện thoại phải là số";
    public String PhoneLength = "Số điện thoại phải bao gồm 10 số";
    public String PhoneRequire = "Vui lòng nhập số điện thoại";
    public String FullNameLength = "Họ tên có độ dài từ 2 đến 20 ký tự";
    public String FullNameRequire = "Vui lòng nhập họ tên";
    public String UserNameLength = "Tên đăng nhập phải từ 4 đến 20 ký tự";
    public String UserNameRequire = "Vui lòng nhập tên đăng nhập";
    public String PasswordLength = "Mật khẩu phải từ 4 đến 20 ký tự";
    public String PasswordRequire = "Vui lòng nhập mật khẩu";
    public String PasswordCompare = "Mật khẩu và mật khẩu nhập lại khác nhau";
    public String ConfirmPasswordRequire = "Vui lòng nhập xác nhận mật khẩu";
    public String BillIdLength = "Mã hóa đơn không được lớn hơn 20 kí tự";
    public String BillRequire = "Vui lòng nhập mã hóa đơn";
    public String BillFormat = "Mã hóa đơn phải có định dạng HD...";
    public String CustomerNameLength = "Tên khách hàng phải từ 2 đến 20 kí tự";
    public String CustomerNameRequire = "Vui lòng nhập tên khách hàng";
    public String DateRequire = "Vui lòng nhập ngày tháng";
    public String ServiceIdLength = "Mã dịch vụ không được lớn hơn 20 kí tự";
    public String ServiceIdRequire = "Vui lòng nhập mã dịch vụ";
    public String ServiceIdFormat = "Mã dịch vụ phải có định dạng DV...";
    public String ServiceTypeLength = "Loại dịch vụ không được lớn hơn 20 kí tự";
    public String ServiceTypeRequire = "Vui lòng nhập loại dịch vụ";
    public String ProductTypeLength = "Loại sản phẩm không được lớn hơn 20 kí tự";
    public String ProductTypeRequire = "Vui lòng nhập loại sản phẩm";
    public String UnitLength = "Đơn vị không được lớn hơn 20 kí tự";
    public String UnitRequire = "Vui lòng nhập đơn vị";
    public String PriceLength = "Đơn giá không được lớn hơn 20 kí tự";
    public String PriceRequire = "Vui lòng nhập đơn giá";
    public String PriceInvalid = "Đơn giá phải là số";
    public String WarehouseIdLength = "Mã kho không được lớn hơn 20 kí tự";
    public String WarehouseIdRequire = "Vui lòng nhập mã kho";
    public String WarehouseIdFormat = "Mã kho phải có định dạng K...";
    public String WarehouseNameLength = "Tên kho không được lớn hơn 20 kí tự";
    public String WarehouseNameRequire = "Vui lòng nhập tên kho";

    public Pattern NSCPattern=Pattern.compile("[A-Za-z0-9AÁÀẢÃẠÂẤẦẨẪẬĂẮẰẲẴẶEÉÈẺẼẸÊẾỀỂỄỆIÍÌỈĨỊOÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢUÚÙỦŨỤƯỨỪỬỮỰYÝỲỶỸỴĐaáàảãạâấầẩẫậăắằẳẵặeéèẻẽẹêếềểễệiíìỉĩịoóòỏõọôốồổỗộơớờởỡợuúùủũụưứừửữựyýỳỷỹỵđ ]+");

    public void removeErrorText(TextInputLayout textInputLayout, TextInputEditText textInputEditText){
        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            textInputLayout.setError("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}
