package cn.diffpi.config;
import cn.dreampie.server.provider.jetty.JettyServerProvider;

public class ExampleApplication {

  public static void main(String[] args) throws Exception {
    new JettyServerProvider().setContextPath("/diffpi").build().start();
  }
}