@RestController
@RequestMapping("/api/repCodeOwners")
public class RepCodeOwnersSearchController {

    private final FAToolService faToolService;

    public RepCodeOwnersSearchController(FAToolService faToolService) {
        this.faToolService = faToolService;
    }

    @PostMapping("/search")
    public ResponseEntity<List<RepCodeOwnersSearchResponse>> searchRepCodeOwnersByECN(
            @RequestHeader("ecn") String ecn) {
        try {
            List<RepCodeOwnersSearchResponse> repCodeOwners = faToolService.searchRepCodeOwnersByECN(ecn);
            return ResponseEntity.ok(repCodeOwners);
        } catch (Exception e) {
            // Handle any exceptions or errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}


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
