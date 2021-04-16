# Maven Shade Merge Manifest Transformer

## Fonctionnalités générales
Cette librairie pour le plugin Shade permet de concaténer les différents manifest et d'injecter vos propres valeurs.

## Exemple
Dans l'exemple ci dessous, on utilise le plugin shade pour injecter du code et 
la dépendance permet de récuperer le manifest d'origine et d'injecter une valeur, dans l'exemple, on veut rajouter le Premain-Class

````xml
<plugin>
	<groupId>org.apache.maven.plugins</groupId>
	<artifactId>maven-shade-plugin</artifactId>
	<version>3.2.4</version>
	<dependencies>
		<dependency>
			<groupId>com.github.marcosemiao.maven.plugins.shade.resource</groupId>
			<artifactId>maven-shade-merge-manifest-transformer</artifactId>
			<version>0.0.1</version>
		</dependency>
	</dependencies>
	<configuration>
		<transformers>
			<transformer implementation="fr.ms.maven.shade.transformers.MergeManifestResourceTransformer">
				<manifestEntries>
					<Premain-Class>nouveau.Agent</Premain-Class>
				</manifestEntries>
			</transformer>
		</transformers>
	</configuration>
</plugin>
````