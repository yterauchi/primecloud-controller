/*
 * Copyright 2015 by SCSK Corporation.
 * 
 * This file is part of PrimeCloud Controller(TM).
 * 
 * PrimeCloud Controller(TM) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 * 
 * PrimeCloud Controller(TM) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with PrimeCloud Controller(TM). If not, see <http://www.gnu.org/licenses/>.
 */
package jp.primecloud.auto.api;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import jp.primecloud.auto.api.response.DescribePlatformResponse;
import jp.primecloud.auto.api.response.PlatformAwsResponse;
import jp.primecloud.auto.api.response.PlatformAzureResponse;
import jp.primecloud.auto.api.response.PlatformCloudstackResponse;
import jp.primecloud.auto.api.response.PlatformNiftyResponse;
import jp.primecloud.auto.api.response.PlatformOpenstackResponse;
import jp.primecloud.auto.api.response.PlatformResponse;
import jp.primecloud.auto.api.response.PlatformVcloudResponse;
import jp.primecloud.auto.api.response.PlatformVmwareResponse;
import jp.primecloud.auto.entity.crud.AwsCertificate;
import jp.primecloud.auto.entity.crud.CloudstackCertificate;
import jp.primecloud.auto.entity.crud.Platform;
import jp.primecloud.auto.entity.crud.PlatformAws;
import jp.primecloud.auto.entity.crud.PlatformAzure;
import jp.primecloud.auto.entity.crud.PlatformCloudstack;
import jp.primecloud.auto.entity.crud.PlatformNifty;
import jp.primecloud.auto.entity.crud.PlatformOpenstack;
import jp.primecloud.auto.entity.crud.PlatformVcloud;
import jp.primecloud.auto.entity.crud.PlatformVmware;
import jp.primecloud.auto.entity.crud.User;
import jp.primecloud.auto.exception.AutoApplicationException;
import jp.primecloud.auto.service.dto.KeyPairDto;
import jp.primecloud.auto.service.dto.SecurityGroupDto;
import jp.primecloud.auto.service.dto.SubnetDto;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * プラットフォーム情報取得
 * </p>
 */
@Path("/DescribePlatform")
public class DescribePlatform extends ApiSupport {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public DescribePlatformResponse describePlatform(@QueryParam(PARAM_NAME_PLATFORM_NO) String platformNo) {
        // 入力チェック
        ApiValidate.validatePlatformNo(platformNo);

        // ユーザ取得
        User user = checkAndGetUser();

        // プラットフォーム取得
        Platform platform = platformDao.read(Long.parseLong(platformNo));

        // プラットフォームが存在しない場合
        if (platform == null) {
            throw new AutoApplicationException("EAPI-100000", "Platform", PARAM_NAME_PLATFORM_NO, platformNo);
        }

        // プラットフォームを選択できない場合
        if (BooleanUtils.isNotTrue(platform.getSelectable())) {
            throw new AutoApplicationException("EAPI-000020", "Platform", PARAM_NAME_PLATFORM_NO, platformNo);
        }

        // プラットフォームを利用できない場合
        if (!platformService.isUseablePlatforms(user.getUserNo(), platform)) {
            throw new AutoApplicationException("EAPI-000020", "Platform", PARAM_NAME_PLATFORM_NO, platformNo);
        }

        PlatformResponse platformResponse = new PlatformResponse(platform);

        // AWS
        if (PLATFORM_TYPE_AWS.equals(platform.getPlatformType())) {
            PlatformAwsResponse awsResponse = getAwsDetail(user.getUserNo(), platform.getPlatformNo());
            platformResponse.setAws(awsResponse);
        }
        // VMware
        else if (PLATFORM_TYPE_VMWARE.equals(platform.getPlatformType())) {
            PlatformVmwareResponse vmwareResponse = getVmwareDetail(user.getUserNo(), platform.getPlatformNo());
            platformResponse.setVmware(vmwareResponse);
        }
        // Nifty
        else if (PLATFORM_TYPE_NIFTY.equals(platform.getPlatformType())) {
            PlatformNiftyResponse niftyResponse = getNiftyDetail(user.getUserNo(), platform.getPlatformNo());
            platformResponse.setNifty(niftyResponse);
        }
        // CloudStack
        else if (PLATFORM_TYPE_CLOUDSTACK.equals(platform.getPlatformType())) {
            PlatformCloudstackResponse csResponse = getCloudstackDetail(user.getUserNo(), platform.getPlatformNo());
            platformResponse.setCloudstack(csResponse);
        }
        // vCloud
        else if (PLATFORM_TYPE_VCLOUD.equals(platform.getPlatformType())) {
            PlatformVcloudResponse vcloudResponse = getVcloudDetail(user.getUserNo(), platform.getPlatformNo());
            platformResponse.setVcloud(vcloudResponse);
        }
        // Azure
        else if (PLATFORM_TYPE_AZURE.equals(platform.getPlatformType())) {
            PlatformAzureResponse azureResponse = getAzureDetail(user.getUserNo(), platform.getPlatformNo());
            platformResponse.setAzure(azureResponse);
        }
        // OpenStack
        else if (PLATFORM_TYPE_OPENSTACK.equals(platform.getPlatformType())) {
            PlatformOpenstackResponse openstackResponse = getOpenstackDetail(user.getUserNo(), platform.getPlatformNo());
            platformResponse.setOpenstack(openstackResponse);
        }

        DescribePlatformResponse response = new DescribePlatformResponse(platformResponse);
        response.setSuccess(true);

        return response;
    }

    private PlatformAwsResponse getAwsDetail(Long userNo, Long platformNo) {
        PlatformAws aws = platformAwsDao.read(platformNo);
        PlatformAwsResponse response = new PlatformAwsResponse(aws);

        // キー名
        List<KeyPairDto> keyPairDtos = iaasDescribeService.getKeyPairs(userNo, platformNo);
        for (KeyPairDto keyPairDto : keyPairDtos) {
            response.getKeyNames().add(keyPairDto.getKeyName());
        }

        // セキュリティグループ
        List<SecurityGroupDto> securityGroupDtos = iaasDescribeService.getSecurityGroups(userNo, platformNo,
                aws.getVpcId());
        for (SecurityGroupDto securityGroupDto : securityGroupDtos) {
            response.getSecurityGroups().add(securityGroupDto.getGroupName());
        }

        // デフォルトキーペア
        AwsCertificate certificate = awsCertificateDao.read(userNo, platformNo);
        response.setDefKeyPair(StringUtils.isEmpty(certificate.getDefKeypair()) ? null : certificate.getDefKeypair());

        // サブネット
        if (BooleanUtils.isTrue(aws.getVpc())) {
            List<SubnetDto> subnetDtos = iaasDescribeService.getSubnets(userNo, platformNo, aws.getVpcId());
            for (SubnetDto subnetDto : subnetDtos) {
                response.getSubnets().add(subnetDto.getCidrBlock());
            }

            // デフォルトサブネット
            response.setDefSubnet(getCidrBlockBySubnetId(subnetDtos, certificate.getDefSubnet()));
            response.setDefLbSubnet(getCidrBlockBySubnetId(subnetDtos, certificate.getDefSubnet()));
        }

        return response;
    }

    private PlatformCloudstackResponse getCloudstackDetail(Long userNo, Long platformNo) {
        PlatformCloudstack cloudstack = platformCloudstackDao.read(platformNo);
        PlatformCloudstackResponse response = new PlatformCloudstackResponse(cloudstack);

        //デフォルトキーペア
        CloudstackCertificate certificate = cloudstackCertificateDao.read(userNo, platformNo);
        response.setDefKeyPair(StringUtils.isEmpty(certificate.getDefKeypair()) ? null : certificate.getDefKeypair());

        return response;
    }

    private PlatformVmwareResponse getVmwareDetail(Long userNo, Long platformNo) {
        PlatformVmware vmware = platformVmwareDao.read(platformNo);
        return new PlatformVmwareResponse(vmware);
    }

    private PlatformNiftyResponse getNiftyDetail(Long userNo, Long platformNo) {
        PlatformNifty nifty = platformNiftyDao.read(platformNo);
        return new PlatformNiftyResponse(nifty);
    }

    private PlatformVcloudResponse getVcloudDetail(Long userNo, Long platformNo) {
        PlatformVcloud vcloud = platformVcloudDao.read(platformNo);
        return new PlatformVcloudResponse(vcloud);
    }

    private PlatformOpenstackResponse getOpenstackDetail(Long userNo, Long platformNo) {
        PlatformOpenstack openstack = platformOpenstackDao.read(platformNo);
        return new PlatformOpenstackResponse(openstack);
    }

    private PlatformAzureResponse getAzureDetail(Long userNo, Long platformNo) {
        PlatformAzure azure = platformAzureDao.read(platformNo);
        return new PlatformAzureResponse(azure);
    }

    private String getCidrBlockBySubnetId(List<SubnetDto> subnetDtos, String subnetId) {
        StringBuffer buffer = new StringBuffer();
        if (StringUtils.isNotEmpty(subnetId)) {
            List<String> subnetIds = new ArrayList<String>();
            for (String tmpSubnet : subnetId.split(",")) {
                subnetIds.add(tmpSubnet.trim());
            }
            for (SubnetDto subnetDto : subnetDtos) {
                if (subnetIds.contains(subnetDto.getSubnetId())) {
                    buffer.append(buffer.length() > 0 ? "," + subnetDto.getCidrBlock() : subnetDto.getCidrBlock());
                }
            }
        }
        return buffer.length() > 0 ? buffer.toString() : null;
    }
}