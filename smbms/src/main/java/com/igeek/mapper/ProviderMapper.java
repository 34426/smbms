package com.igeek.mapper;

import com.igeek.pojo.Provider;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProviderMapper {
    public Provider findById(@Param("id") long id);

    public List<Provider> findByCodeAndName(@Param("proCode") String proCode,@Param("proName") String proName);

    public int insertProvider(Provider provider);

    public int deleteById(long id);

    public int updateProvider(Provider provider);

    public List<Provider> findAll();
}
