Istruzioni per l'installazione

1. Impostare nel file setting.xml nella cartella .m2


  <profiles>

	<profile>
      <id>inject-liferay-properties</id>
       <properties>
         <liferay.version>6.2.0</liferay.version>
         <liferay.auto.deploy.dir>C:/Lavoro/Java/liferay-portal-6.2-ce-ga2-tomcat7/deploy</liferay.auto.deploy.dir>
         <liferay.app.server.dir>C:/Lavoro/Java/liferay-portal-6.2-ce-ga2-tomcat7/tomcat-7.0.42</liferay.app.server.dir>
         <liferay.app.server.deploy.dir>C:/Lavoro/Java/liferay-portal-6.2-ce-ga2-tomcat7/tomcat-7.0.42/webapps</liferay.app.server.deploy.dir>
        <liferay.app.server.lib.global.dir>C:/Lavoro/Java/liferay-portal-6.2-ce-ga2-tomcat7/tomcat-7.0.42/lib/ext</liferay.app.server.lib.global.dir>
        <liferay.app.server.portal.dir>C:/Lavoro/Java/liferay-portal-6.2-ce-ga2-tomcat7/tomcat-7.0.42/webapps/ROOT</liferay.app.server.portal.dir>
     </properties>
    </profile>
	
  </profiles>

  <activeProfiles>
  	<activeProfile>inject-liferay-properties</activeProfile>
	
  </activeProfiles>
