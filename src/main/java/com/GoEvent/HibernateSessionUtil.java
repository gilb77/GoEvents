package com.GoEvent;


public class HibernateSessionUtil {
//
//    private static SessionFactory sessionFactory;
//    private static String PROPERTY_FILE_NAME;
//
//    public static SessionFactory getSessionFactory() throws IOException {
//        return getSessionFactory(null);
//    }
//
//    public static SessionFactory getSessionFactory(String propertyFileName) throws IOException {
//        PROPERTY_FILE_NAME = propertyFileName;
//        if (sessionFactory == null) {
//            ServiceRegistry serviceRegistry = configureServiceRegistry();
//            sessionFactory = makeSessionFactory(serviceRegistry);
//        }
//        return sessionFactory;
//    }
//
//    private static SessionFactory makeSessionFactory(ServiceRegistry serviceRegistry) {
//        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
//        metadataSources.addAnnotatedClass(Movie.class);
//        metadataSources.addAnnotatedClass(Cinema.class);
//        metadataSources.addAnnotatedClass(Theater.class);
//
//        Metadata metadata = metadataSources.buildMetadata();
//        return metadata.getSessionFactoryBuilder()
//                .build();
//
//    }
//
//    private static ServiceRegistry configureServiceRegistry() throws IOException {
//        Properties properties = getProperties();
//        return new StandardServiceRegistryBuilder().applySettings(properties)
//                .build();
//    }
//
//    private static Properties getProperties() throws IOException {
//        Properties properties = new Properties();
//        URL propertiesURL = Thread.currentThread()
//                .getContextClassLoader()
//                .getResource(StringUtils.defaultString(PROPERTY_FILE_NAME, "hibernate.properties"));
//        try (FileInputStream inputStream = new FileInputStream(propertiesURL.getFile())) {
//            properties.load(inputStream);
//        }
//        return properties;
//    }
}