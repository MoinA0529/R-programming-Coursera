@Test
    void testUserEntityManager() {
        // Arrange
        when(config.userDataSource()).thenReturn(dataSource);
        
        // Act
        LocalContainerEntityManagerFactoryBean result = config.userEntityManager();
        
        // Assert
        assertNotNull(result, "EntityManagerFactoryBean should not be null");
        assertNotNull(result.getDataSource(), "DataSource should not be null");
        assertNotNull(result.getJpaVendorAdapter(), "JpaVendorAdapter should not be null");
    }

    @Test
    void testUserTransactionManager() {
        // Arrange
        when(config.userEntityManager()).thenReturn(new LocalContainerEntityManagerFactoryBean());
        
        // Act
        PlatformTransactionManager result = config.userTransactionManager();
        
        // Assert
        assertNotNull(result, "TransactionManager should not be null");
    }
