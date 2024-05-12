List<RepCodeRelationItem> repCodeRelationItems = new ArrayList<>();
        for (RepCodeOwnersSearchResponse response : responseDetails.getData()) {
            RepCodeRelationItem item = new RepCodeRelationItem();
            item.setRepCode(response.getRepCode());
            item.setSubFirm(response.getSubFirm());
            item.setBranch(response.getBranch());
            
            List<TeamMemberRelationItem> memberItems = new ArrayList<>();
            for (MemberData memberData : response.getMembers()) {
                TeamMemberRelationItem memberItem = new TeamMemberRelationItem();
                memberItem.setName(memberData.getName());
                memberItem.setShowToClient(memberData.isShowToClient());
                memberItem.setIndex(memberData.getIndex());
                memberItem.setReceiveEmail(memberData.isReceiveEmail());
                memberItem.setEmployeeId(memberData.getEmployeeId());
                memberItem.setPpid(memberData.getPpid());
                memberItem.setStatus(memberData.getStatus());
                memberItems.add(memberItem);
            }
            item.setMembers(memberItems);
