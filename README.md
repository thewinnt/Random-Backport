# /random backport
Adds the /random roll command to 1.20.1, also updates it a little:

Syntax: `/random roll <range> [amount]`
- `range` is a range of numbers written like this: `<min>..<max>` (inclusive)
- `amount` is an optional amount of numbers to be returned. If larger than 1, the command returns the 
  first value as its result

The command doesn't require operator permissions and outputs a nice colored message to all players.

Supports Fabric/Forge 1.20.1, doesn't have to be installed on the client (unless you want it in singleplayer).
Does **NOT** need Fabric API or Architectury API.

This won't be ported to 1.20.2 and later, as Mojang added the command themselves.