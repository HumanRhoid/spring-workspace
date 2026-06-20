package com.example.member;

// ===== 기존 코드 (JPA 전환으로 대체됨) =====
// @Repository
// public class MemoryMemberRepository implements MemberRepository {
//     private static final Map<Long, Member> store = new HashMap<>();
//     private static long sequence = 0L;
//
//     @Override
//     public Member save(Member member) {
//         member.setId(++sequence);
//         store.put(member.getId(), member);
//         return member;
//     }
//
//     @Override
//     public Optional<Member> findByID(Long id) {
//         return Optional.ofNullable(store.get(id));
//     }
//
//     @Override
//     public Optional<Member> findByName(String name) {
//         return store.values().stream()
//                 .filter(m -> m.getName().equals(name))
//                 .findAny();
//     }
//
//     @Override
//     public List<Member> findAll() {
//         return new ArrayList<>(store.values());
//     }
//
//     public void clearStore() {
//         store.clear();
//     }
// }
