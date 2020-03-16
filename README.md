# openfeign-url-encoding-issue
URL encoding issue introduced in OpenFeign 10.7.1

OpenFeign drastically changed how elements of the url are encoded in https://github.com/OpenFeign/feign/pull/1138

In particular, path segments are not encoded correctly:
- the colon character `:` is now encoded to `%3A` although it's not required
- the forward slash `/` is not automatically encoded anymore which means that it gets 
resolved as a path segment separator instead 

This project uses Spring Cloud OpenFeign to demonstrate the encoding issue. Simply
run the tests in `ExampleClientTest` to see the failures.