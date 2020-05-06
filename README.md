<h1>Table of Contents</h1>

- [Android multithreading & http request demo](#android-multithreading--http-request-demo)
  - [Demo application - JsonplaceholderClient](#demo-application---jsonplaceholderclient)
  - [Pre-requisites](#pre-requisites)
- [Struktur aplikasi](#struktur-aplikasi)
  - [Dependencies](#dependencies)
  - [Architecture & app packages](#architecture--app-packages)
- [Retrofit](#retrofit)
- [Closing](#closing)

# Android multithreading & http request demo
## Demo application - JsonplaceholderClient
Untuk tutorial multithreading & http request, aplikasi yang dibuat adalah client untuk menampilkan data dari `https://jsonplaceholder.typicode.com/`.

Untuk HTTP request, aplikasi ini menggunakan HTTP client `retrofit` (*https://square.github.io/retrofit/*)

Aplikasi yang dibuat akan memiliki 1 halaman `MainActivity` yang bertugas menampilkan data `posts` dari jsonplaceholder.

Beberapa bagian dari aplikasi ini dengan sengaja di-simplify untuk keringkasan.

## Pre-requisites

Untuk menjalankan aplikasi ini, diperlukan:
- Java & Android Studio
- Emulator

# Struktur aplikasi

## Dependencies

Beberapa dependency aplikasi antara lain:
- ***Retrofit*** -- HTTP client untuk melakukan http call
- ***Retrofit-Converter Gson*** -- Serializer yang digunakan secara internal oleh retrofit
- ***Butterknife*** -- Untuk mempermudah view binding
- ***Timber*** -- Better logging
- ***Recyclerview*** -- Untuk menampilkan data dalam list

## Architecture & app packages

Aplikasi ini disusun dengan menggunakan arsitektur *MVP (Model-View-Presenter)*, dimana setiap halaman akan memiliki:
1. ***Activity (view)*** -- bertugas menampilkan data yang dikirim oleh *presenter* pada UI
2. ***Presenter*** -- bertugas sebagai abstraksi data processing

Terdapat beberapa package didalam aplikasi demo ini, dimana setiap package me-representasikan fungsi yang berbeda-beda:
- `app` -- package ini berisikan halaman-halaman yang ada dalam aplikasi
- `model` -- model classes
- `providers` -- singleton yang menyediakan dependency aplikasi
- `proxy` -- abstraksi pemanggilan data via HTTP

# Retrofit

*Retrofit* adalah HTTP client yang normalnya digunakan pada java applications untuk membantu proses http call.

Untuk menggunakan *retrofit*, tambahkan dependency berikut pada `build.gradle` module aplikasi lalu jangan lupa untuk *gradle sync*
```java
ext {
    retrofitVersion = '2.3.0'
}

dependencies {
    ... 
    implementation "com.squareup.retrofit2:retrofit:$retrofitVersion"
    implementation "com.squareup.retrofit2:converter-gson:$retrofitVersion"
}
```

Setelah menambahkan dependency, untuk dapat melakukan http call, kita perlu terlebih dahulu membuat *instance* dari `Retrofit`.
```java
public final class RetrofitProvider {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    
    private static Retrofit retrofit = null;
    
    public static synchronized Retrofit getRetrofit () {
        if (retrofit == null) {
            // Initialize retrofit
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
```

Class ini merupakan *singleton* yang bertugas untuk menyediakan retrofit instance. Ketika membuat *instance* dari `Retrofit`, kita dapat memanfaatkan `Retrofit.Builder`.

`Retrofit.Builder` memiliki beberapa *setter*, antara lain (yang digunakan pada aplikasi ini):
- `baseUrl(String baseUrl)` -- Menentukan base URL dari HTTP endpoint yang akan digunakan untuk fetch data
- `addConverterFactory(Converter.Factory factory)` -- Menentukan converter yang digunakan untuk *data serialization*

Setelah membuat *provider* untuk menyediakan `Retrofit` instance, kita perlu menentukan *endpoint* yang akan digunakan untuk HTTP call.

Pada halaman *https://jsonplaceholder.typicode.com*, maka kita dapat melihat bahwa untuk fetch semua data `posts`, endpoint yang diperlukan adalah *https://jsonplaceholder.typicode.com/posts*

Retrofit mengizinkan kita untuk men-deklarasikan endpoint dengan membuat sebuah interface, dan menambahkan method yang di *annotate* dengan annotation dari retrofit

```java
public interface PostEndpoint {
    @GET("posts")
    Call<List<Post>> getPosts();
}
```

Model class yang digunakan:
```java
public class Post {
    private int id;
    private String title;
    private String body;
    private int userId;

    // setter & getters ...
}
```

Disini kita mendeklarasikan sebuah *endpoint* dengan HTTP method ***GET***, dan hasil dari HTTP call ini akan memberikan data berupa `List<Post>`. Untuk setiap response, retrofit membutuhkan kita untuk wrap data classnya didalam `Call`.

`Call` menandakan pemanggilan request oleh retrofit ke http endpoint yang ditentukan, dan akan menghasilkan response.

Setelah mendeklarasikan endpoint yang dapat dipanggil, pemanggilan endpoint tersebut dapat dilakukan sebagai berikut:

```java
public class PostClient {
    private PostEndpoint postEndpoint = RetrofitProvider.getRetrofit().create(PostEndpoint.class);
    
    public Call<List<Post>> getPosts() {
        return postEndpoint.getPosts();
    }
}
```

Retrofit menyediakan method `retrofit.create(interface i)` untuk membuat instance dari interface berisikan endpoints yang sudah kita buat. Dengan menggunakan instance dari interface tersebut, kita hanya perlu memanggil method yang telah di deklarasi *(`getPosts`)*

Selanjutnya, `PostClient` dapat digunakan sebagai berikut:
```java
PostClient postClient = new PostClient();
final Call<List<Post>> postsCall = postClient.getPosts();
        
// Asynchronously send the request and notify callback of its response
postsCall.enqueue(new Callback<List<Post>>() {
    @Override
    public void onResponse (Call<List<Post>> call, Response<List<Post>> response) {
        if(response.body() != null) {
            final List<Post> posts = response.body();
            Timber.d("Fetched %d posts", posts.size());
        }
    }

    @Override
    public void onFailure (Call<List<Post>> call, Throwable t) {
        Timber.e(t);
    }
});
```

Retrofit mengizinkan kita untuk melakukan sebuah request secara *synchronous* maupun *asynchronous*. 

Untuk melakukan request secara *asynchronous*, dapat dilakukan dengan memanggil method `enqueue()` dan menyediakan *callback* ketika response sudah didapatkan. (sesuai dengan contoh diatas) 

Untuk melakukan request secara synchronous, dapat dilakukan dengan menggunakan method `execute()` seperti berikut:

```java
postClient.getPosts().execute()
```

> Pada android, sangat disarankan untuk melakukan IO bound operations seperti HTTP call secara asynchronous agar tidak blocking pada main thread, yang nantinya dapat berpotensi menyebabkan ANR (Application Not Responding)

# Closing

Berikut adalah demo aplikasi yang menggunakan Retrofit untuk fetch data jsonplaceholder.  
Any questions? you can reach me at:
- `line`: fernandochristyanto