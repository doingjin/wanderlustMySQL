package model.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("MemberService")
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberDAO dao;
	
	@Override
	public void insertM(MemberVO vo) {
		dao.insertM(vo);
	}

	@Override
	public void updateM(MemberVO vo) {
		dao.updateM(vo);
	}
	
	@Override
	public void updatePW(MemberVO vo) {
		dao.updatePW(vo);
	}

	@Override
	public void deleteM(MemberVO vo) {
		dao.deleteM(vo);
	}

	@Override
	public MemberVO getM(MemberVO vo) {
		try {
			System.out.println("service"+vo.getMid());
			return dao.getM(vo);
		} catch(Exception e) {
			System.out.println("실패?"+e);
			return null;
		}
	}

	@Override
	public MemberVO getMail(MemberVO vo) {
		try {
			return dao.getMail(vo);
		} catch(Exception e) {
			return null;
		}
	}

}
