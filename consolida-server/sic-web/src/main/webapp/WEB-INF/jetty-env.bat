<?xml version="1.0"  encoding="UTF-8"?>
<!DOCTYPE Configure PUBLIC "-//Mort Bay Consulting//DTD Configure//EN" "http://www.eclipse.org/jetty/configure_9_0.dtd">

<Configure id ="sicCtx" class="org.eclipse.jetty.webapp.WebAppContext">
	<Set name="contextPath">/sic-web</Set>
    <New id="sicDS" class="org.eclipse.jetty.plus.jndi.Resource">
<!--  		<Arg><Ref refid="sicCtx"/></Arg> -->
        <Arg>java:comp/env/jdbc/datasources/sinDS</Arg>
        <Arg>
            <New class="com.mysql.cj.jdbc.MysqlConnectionPoolDataSource">
                <Set name="driverClassName">com.mysql.jdbc.Driver</Set>
                <Set name="url">jdbc:mysql://localhost:3306/sin?autoReconnect=true&amp;useSSL=false</Set>
                <Set name="user">root</Set>
                <Set name="password">root</Set>
            </New>
        </Arg>
    </New>
</Configure>
