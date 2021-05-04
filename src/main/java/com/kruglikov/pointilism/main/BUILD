java_library(
    name = "main_lib",
    srcs = glob(["*.java"]),
    deps = [
        "//src/main/java/com/kruglikov/pointilism/index",
        "//src/main/java/com/kruglikov/pointilism/store",
        "//src/main/java/com/kruglikov/pointilism/utils",
    ],
)

java_binary(
    name = "main",
    srcs = ["main.java"],
    main_class = "com.kruglikov.pointilism.main.main",
    deps = [
        "//src/main/java/com/kruglikov/pointilism/index",
        "//src/main/java/com/kruglikov/pointilism/store",
        "//src/main/java/com/kruglikov/pointilism/utils",
        "//src/main/java/com/kruglikov/pointilism/compositer",
    ],
)

java_binary(
    name = "alternateMain",
    srcs = ["alternateMain.java"],
    main_class = "com.kruglikov.pointilism.main.alternateMain",
    deps = [
        "//src/main/java/com/kruglikov/pointilism/index",
        "//src/main/java/com/kruglikov/pointilism/store",
        "//src/main/java/com/kruglikov/pointilism/utils",
    ],
)

java_binary(
    name = "luminanceMain",
    srcs = ["runLuminance.java"],
    main_class = "com.kruglikov.pointilism.main.runLuminance",
    deps = [
        "//src/main/java/com/kruglikov/pointilism/index",
        "//src/main/java/com/kruglikov/pointilism/store",
        "//src/main/java/com/kruglikov/pointilism/utils",
    ],
)
