class UserInfo {
  String? name;
  String? email;
  String? phone;

  UserInfo({this.name, this.email, this.phone});

  factory UserInfo.fromJson(dynamic json) {
    return UserInfo(
      name: json["name"].toString(),
      email: json["email"].toString(),
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'name': name,
      'email': email,
      'phone': phone,
    };
  }
}
